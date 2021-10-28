package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel
        while (p != null){
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }
        p = new Node<>(verdi, q);

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        antall++;
        return true;                             // vellykket innlegging
    }

    public boolean fjern(T verdi) {
       // throw new UnsupportedOperationException("Ikke kodet ennå!");
        if (verdi == null) return false;
        Node<T> p = rot, q = null;
        while (p != null) {
            int cmp = comp.compare(verdi,p.verdi);
            if (cmp < 0) { q = p; p = p.venstre; }
            else if (cmp > 0) { q = p; p = p.høyre; }
            else break;
        }

        if (p == null) return false;
        if (p.venstre == null || p.høyre == null){
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if (b != null) b.forelder = q;
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
        }

        else {
            Node<T> s = p, r = p.høyre;
            while (r.venstre != null) {
                s = r;
                r = r.venstre;
            }
            p.verdi = r.verdi;

            if (r.høyre != null) r.høyre.forelder = s;

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        antall--;
        return true;
    }

    public int fjernAlle(T verdi) {
        int antall = 0;
        while (fjern(verdi)) antall++;
        return antall;
    }

    public int antall(T verdi) {
       // throw new UnsupportedOperationException("Ikke kodet ennå!");

        // Sjekker om det finnes noder i arrayet
        if (verdi == null) {
            return 0;
        }

        int antall = 0;
        Node<T> p = rot;

        while (p != null) {
            // Sjekker om "verdi" finnes i treet
            // Returnerer -1 for mindre, 0 for lik og 1 for større
            int cmp = comp.compare(verdi, p.verdi);

            // Sjekker om verdien vi søker antall av er mindre
            if (cmp == -1) {
                p = p.venstre;
                // Sjekker om verdien vi søker er lik eller større-.
            } else {
                if (cmp == 0) {
                    // Oppdaterer antall hvis verdiene fra treet og den
                    // vi søker er like
                    antall++;
                }
                p = p.høyre;
            }
        }
        // Returnere antall
        return antall;
    }

    public void nullstill() {

        if (rot == null) {
            return;
        }

        ArrayList<Node> liste = new ArrayList();
        ArrayDeque<Node> stack = new ArrayDeque<>();

        while (rot != null || stack.size() > 0) {
            while (rot != null) {
                stack.push(rot);
                rot = rot.høyre;
            }
            rot = stack.pop();
            liste.add(rot);
            rot = rot.venstre;
        }

        for(Node node: liste) {
            node.forelder = null;
            node.venstre = null;
            node.høyre = null;
            node.verdi = null;
        }

        antall = 0;
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        // Fra kompendiet 5.1.7
        // -> "Den siste i preorden er lik den første i speilvendt postorden."

        while (true) {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        // Sjekker om forelder er null, og returnerer da "null"
        if (p.forelder == null) {
            return null;

            // Sjekker i treet om mulige plasseringer av nestPostorden
        } else if (p == p.forelder.venstre) {
            if (p.forelder.høyre != null) {
                p = p.forelder.høyre;
                // Eneste mulig posisjon av nestePostorden blir da
                // å finne p sin førstePostorden
                return førstePostorden(p);
            }
        }
        return p.forelder;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
       // throw new UnsupportedOperationException("Ikke kodet ennå!");

        // Bruk av rekursjon til å løse oppgaven. Ganske likt forrige eksemplet
        if(p.venstre != null) {
            postordenRecursive(p.venstre, oppgave);
        } if(p.høyre != null) {
            postordenRecursive(p.høyre,oppgave);
        }
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        // throw new UnsupportedOperationException("Ikke kodet ennå!");
      /*  ArrayList<T> data = new ArrayList<>();
        serializeHjelper(rot, data);
        return data;
    }

    private void serializeHjelper(Node verdi, ArrayList<T> data){
        if (verdi == null) {
            return;
        }
        data.add(rot.verdi);
            serializeHjelper(verdi.venstre, data);
            serializeHjelper(verdi.høyre, data);
*/
        ArrayList<T> liste = new ArrayList<>();
        ArrayDeque<Node<T>> queue = new ArrayDeque<>();

        queue.addLast(rot);

        while (!queue.isEmpty()) {
            Node<T> curr = queue.removeLast();

            if (curr.venstre != null) {
                queue.addLast(curr.venstre);
            }

            if (curr.høyre != null) {
                queue.addLast(curr.høyre);
            }

            liste.add(curr.verdi);
        }

        //return new ArrayList<>(liste);
        return liste;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        // throw new UnsupportedOperationException("Ikke kodet ennå!");
        ArrayList<K> liste = new ArrayList<>(data);
        SBinTre<K> tre = new SBinTre<>(c);
        for (K verdi : liste) {
            tre.leggInn(verdi);
        }
        return tre;
    }

} // ObligSBinTre
