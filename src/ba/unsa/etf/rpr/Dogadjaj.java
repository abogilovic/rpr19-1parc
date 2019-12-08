package ba.unsa.etf.rpr;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Dogadjaj implements Comparable<Dogadjaj> {
    private String naziv;
    private LocalDateTime pocetak, kraj;

    Dogadjaj(String naziv, LocalDateTime pocetak, LocalDateTime kraj) throws NeispravanFormatDogadjaja {
        if (kraj.isBefore(pocetak)) throw new NeispravanFormatDogadjaja("Neispravan format početka i kraja dogadjaja");
        this.naziv = naziv;
        this.pocetak = pocetak;
        this.kraj = kraj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setPocetak(LocalDateTime of) throws NeispravanFormatDogadjaja {
        if (of.isAfter(kraj)) throw new NeispravanFormatDogadjaja("Neispravan format početka i kraja događaja");
        pocetak = of;
    }

    public void setKraj(LocalDateTime of) throws NeispravanFormatDogadjaja {
        if (of.isBefore(pocetak)) throw new NeispravanFormatDogadjaja("Neispravan format početka i kraja događaja");
        kraj = of;
    }

    public LocalDateTime getPocetak() {
        return pocetak;
    }

    public LocalDateTime getKraj() {
        return kraj;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dogadjaj && getClass().equals(obj.getClass())) {
            Dogadjaj d = (Dogadjaj) obj;
            return d.getNaziv().equals(getNaziv()) && d.getPocetak().isEqual(getPocetak()) && d.getKraj().isEqual(getKraj());
        }
        return false;
    }

    @Override
    public String toString() {
        String prioritet = null;
        if (this instanceof DogadjajNiskogPrioriteta)
            prioritet = "nizak prioritet";
        else if (this instanceof DogadjajSrednjegPrioriteta)
            prioritet = "srednji prioritet";
        else if (this instanceof DogadjajVisokogPrioriteta)
            prioritet = "visok prioritet";
        LocalDateTime poc = getPocetak(), kraj = getKraj();
        return String.format("%s (%s) - početak: %02d/%s/%s (%s), kraj: %02d/%s/%s (%s)", getNaziv(), prioritet,
                poc.getDayOfMonth(), poc.getMonthValue(), poc.getYear(), poc.toLocalTime().toString(),
                kraj.getDayOfMonth(), kraj.getMonthValue(), kraj.getYear(), kraj.toLocalTime().toString());
    }

    @Override
    public int compareTo(Dogadjaj dogadjaj) {
        ArrayList<Class> prioriteti = new ArrayList<>(Arrays.asList(DogadjajNiskogPrioriteta.class, DogadjajSrednjegPrioriteta.class, DogadjajVisokogPrioriteta.class));
        int x = Integer.compare(prioriteti.indexOf(getClass()), prioriteti.indexOf(dogadjaj.getClass()));
        if (x != 0) return x;
        return getNaziv().compareTo(dogadjaj.getNaziv());
    }
}
