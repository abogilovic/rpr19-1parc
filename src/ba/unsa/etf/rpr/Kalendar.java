package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Kalendar {
    private ArrayList<Dogadjaj> dogadjaji = new ArrayList<>();

    public void zakaziDogadjaj(Dogadjaj dogadjaj) {
        dogadjaji.add(dogadjaj);
    }

    public List<Dogadjaj> dajKalendar() {
        return dogadjaji;
    }

    public void zakaziDogadjaje(List<Dogadjaj> testniDogadjaji) {
        dogadjaji.addAll(testniDogadjaji);
    }

    public void otkaziDogadjaje(List<Dogadjaj> dogadjaji) {
        this.dogadjaji.removeAll(dogadjaji);
    }

    public void otkaziDogadjaje(Function<Dogadjaj, Boolean> fun) {
        dogadjaji.stream().filter(fun::apply).collect(Collectors.toList()).forEach(this::otkaziDogadjaj);
    }

    public void otkaziDogadjaj(Dogadjaj dogadjaj) {
        dogadjaji.remove(dogadjaj);
    }


    public Map<LocalDate, List<Dogadjaj>> dajKalendarPoDanima() {
        Map<LocalDate, List<Dogadjaj>> ret = new HashMap<>();
        for (Dogadjaj d : dogadjaji)
            ret.computeIfAbsent(d.getPocetak().toLocalDate(), age -> new ArrayList<Dogadjaj>()).add(d);
        return ret;
    }

    public Dogadjaj dajSljedeciDogadjaj(LocalDateTime of) {
        for (Dogadjaj d : dogadjaji)
            if (d.getPocetak().isAfter(of)) return d;
        throw new IllegalArgumentException("Nemate događaja nakon navedenog datuma");
    }

    public List<Dogadjaj> filtrirajPoKriteriju(Function<Dogadjaj, Boolean> fun) {
        return dogadjaji.stream().filter(fun::apply).collect(Collectors.toList());
    }

    public List<Dogadjaj> dajDogadjajeZaDan(LocalDateTime of) {
        List<Dogadjaj> ret = new ArrayList<>();
        for (Dogadjaj d : dogadjaji)
            if (d.getPocetak().getDayOfYear() == of.getDayOfYear() && d.getKraj().getDayOfYear() == of.getDayOfYear())
                ret.add(d);
        return ret;
    }

    public List<Dogadjaj> dajSortiraneDogadjaje(BiFunction<Dogadjaj, Dogadjaj, Integer> fun) {
        dogadjaji.sort(fun::apply);
        return dogadjaji;
    }

    public Set<Dogadjaj> dajSortiranePoPrioritetu() {
        return new TreeSet<>(dogadjaji);
    }

    public boolean daLiSamSlobodan(LocalDateTime pocetak) {
        for (Dogadjaj d : dogadjaji)
            if ((pocetak.isEqual(d.getPocetak()) || pocetak.isAfter(d.getPocetak())) && (pocetak.isEqual(d.getKraj()) || pocetak.isBefore(d.getKraj())))
                return false;
        return true;
    }

    public boolean daLiSamSlobodan(LocalDateTime pocetak, LocalDateTime kraj) {
        if (pocetak.isAfter(kraj)) throw new IllegalArgumentException("Neispravni podaci o početku i kraju");
        for (Dogadjaj d : dogadjaji)
            if (kraj.isAfter(d.getPocetak()) && pocetak.isBefore(d.getKraj())) return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dogadjaji.size() - 1; i++) {
            sb.append(dogadjaji.get(i).toString());
            sb.append('\n');
        }
        sb.append(dogadjaji.get(dogadjaji.size() - 1).toString());
        return sb.toString();
    }
}
