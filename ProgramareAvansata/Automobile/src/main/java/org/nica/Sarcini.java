package org.nica;

import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.SelectionQuery;
import org.nica.model.*;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;
import static org.nica.Util.Transactioner;
import static org.nica.Util.Pair;
public class Sarcini {
    public static void excludeAutomobil(String nrInregistrare)
    {
        Util.getSessionFactory().inTransaction(session ->
        {
            MutationQuery query = session.createMutationQuery("delete from Automobil where nrInregistrare = :nr");
            query.setParameter("nr", nrInregistrare);
            System.out.println(query.executeUpdate());
        });
    }
    public static List<Automobil> getAlfabetic()
    {
        try(Transactioner tr = new Transactioner()) {
            SelectionQuery<Automobil> query = tr.get().createSelectionQuery(
                    "from Automobil order by model.marca.firma.nume asc",
                    Automobil.class);
            return query.getResultList();
        }
    }
    public static List<Automobil> getAlfabeticDesc()
    {
        try(Transactioner tr = new Transactioner()) {
            SelectionQuery<Automobil> query = tr.get().createSelectionQuery(
                    "from Automobil order by model.marca.firma.nume desc",
                    Automobil.class);
            return query.getResultList();
        }
    }
    public static List<Tara> getTari()
    {
        try(Transactioner tr = new Transactioner()) {
            SelectionQuery<Tara> query = tr.get().createSelectionQuery("from Tara", Tara.class);
            return query.getResultList();
        }
    }

    public static Double getPretulMediuDinTara(String tara)
    {
        try (Transactioner tr = new Transactioner()) {
            SelectionQuery<Double> query = tr.get().createSelectionQuery
                    ("select avg (pret) from Model where marca.firma.tara.nume = :tara", Double.class);
            query.setParameter("tara", tara);
            Double ret = query.getSingleResultOrNull();
            if (ret == null) return 0D;
            return ret;
        }

    }

    public static List<Automobil> getIeftinScumpVerde()
    {
        try (Transactioner tr = new Transactioner()) {
            SelectionQuery<Automobil> query = tr.get().createSelectionQuery
                    ("from Automobil "
                                    + "where"
                                    + "   culoare='verde' and "
                                    + "   model.pret in "
                                    + "      ("
                                    + "         (select min (model.pret) from Automobil where culoare='verde'), "
                                    + "         (select max (model.pret) from Automobil where culoare='verde')"
                                    + "      )",
                            Automobil.class);
            return query.getResultList();
        }
    }

    public static void adaugaAutomobil(Automobil automobil) {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();
            session.merge(automobil);
            session.getTransaction().commit();
        }
    }



    @SuppressWarnings("CommentedOutCode")
    public static Pair<List<Firma>, Long> getFirmaNumarMaximal()
    {
        Long mx = getFirmaNrMaxAutomobile();
        List<Long> ids = getFirmeIdsByNrAutomobile(mx);
        List<Firma> firme = getFirmeByIDs (ids);
        /*
        try (Session session = Util.getCurrentSession()) {
            SelectionQuery<Tuple> query = session.createSelectionQuery
                    (
                            "select model.marca.firma.tara.id id, count(*) cnt from Automobil group by id",
                            Tuple.class);
            for (Tuple tp: query.getResultList()) System.out.println("x>" + tp.get("id") + ":" + tp.get("cnt"));
            session.getTransaction().commit();
            return ret;
        } //*/
        return new Pair<>(firme, mx);
    }
    private static List<Firma> getFirmeByIDs(List<Long> ids)
    {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();
            SelectionQuery<Firma> firme = session.createSelectionQuery
                ("from Firma where id in ( :id )", Firma.class);
            firme.setParameterList("id", ids);
            List<Firma> ret = firme.getResultList();
            session.getTransaction().commit();
            return ret;
        }
    }

    static Long getFirmaNrMaxAutomobile() {
        try (Session session = Util.getCurrentSession()) {
            session.beginTransaction();
            SelectionQuery<Long> firmaMax = session.createSelectionQuery
                (
                    "select max (cnt) from (select model.marca.firma.id id, count(*) cnt from Automobil group by  id)",
                    Long.class);
            Long mx = Optional.of(firmaMax.getSingleResultOrNull()).orElse(0L);
            session.getTransaction().commit();
            return mx;
        }
    }
    private static List<Long> getFirmeIdsByNrAutomobile(Long nrAutomobile)
    {
        try (Transactioner tr = new Transactioner()) {
            SelectionQuery<Long> idsQuery;
            if (nrAutomobile > 0) {
                idsQuery = tr.get().createSelectionQuery
                    ("select model.marca.firma.id id from Automobil group by id having count(*) = :mx", Long.class);
                idsQuery.setParameter("mx", nrAutomobile);
                return idsQuery.getResultList();
            }
            idsQuery = tr.get().createSelectionQuery
                ("select model.marca.firma.id id from Automobil group by id", Long.class);
            return idsQuery.getResultList();
        }
    }

    public static void extractBmwAudiToSeparateTable() {
        try (Transactioner tr = new Transactioner()) {
            // Selectam ID-urile campurilor brandurilor BMW si Audi
            List<String> marciNume = List.of("BMW", "Audi");
            SelectionQuery<Automobil> automobileQuery = tr.get().createSelectionQuery(
                    "from Automobil where model.marca.nume in (:marci)",
                    Automobil.class);
            automobileQuery.setParameterList("marci", marciNume); //bind la parametri
            List<Automobil> automobile = automobileQuery.getResultList();

            tr.get().createMutationQuery("delete from AutomobilBmwAudi").executeUpdate();
            //salvam in tabel, folosim merge() sau persist() pentru ca suntem in mileniul III
            for (var v : automobile) tr.get().merge(new AutomobilBmwAudi(v));
        }

    }
    public static List<AutomobilBmwAudi> getBmwAudi (){
        //try cu resursa, va apela automat metoda close() din interfata AutoCloseable
        try (Transactioner tr = new Transactioner()){
            SelectionQuery<AutomobilBmwAudi> automobileBmwAudiQuery = tr.get().createSelectionQuery(
                    "from AutomobilBmwAudi",
                    AutomobilBmwAudi.class);
            return automobileBmwAudiQuery.getResultList();
        }

    }
    public static List<Model> getModele () {
        //try cu resursa, va apela automat metoda close() din interfata AutoCloseable
        try (Transactioner tr = new Transactioner()){
            SelectionQuery<Model> modelQuery = tr.get().createSelectionQuery(
                    "from Model order by marca.firma.tara.nume, marca.firma.nume, marca.nume, nume",
                    Model.class);
            return modelQuery.getResultList();
        }
    }
    public static Model getModel (Long id) {
        //try cu resursa, va apela automat metoda close() din interfata AutoCloseable
        try (Transactioner tr = new Transactioner()){
            SelectionQuery<Model> modelQuery = tr.get().createSelectionQuery(
                    "from Model where id = :id",
                    Model.class);
            modelQuery.setParameter("id", id);
            return modelQuery.getSingleResultOrNull();
        }

    }

}
