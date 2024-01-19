package projects2;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Archivio {
    final static Logger infoLogger = LoggerFactory.getLogger("archivio");
    final static Logger errorLogger = LoggerFactory.getLogger("archivio2");
    public static void main(String[] args) {
        List<ElementiBiblioteca> archivio = new ArrayList<>();

        Libri l1 = new Libri(1,"Il signore degli Anelli - la compagnia dell'anello", LocalDate.of(1939, Month.APRIL, 12),400,"Tolkien","Fantasy");
        Libri l2 = new Libri(2,"Harry Potter e la pietra filosoale", LocalDate.of(2001, Month.SEPTEMBER, 12),220,"Rowling","Fantasy");
        Libri l3 = new Libri(3,"Se questo è un uomo", LocalDate.of(1990, Month.AUGUST, 25),157,"Primo Levi","Biography");

        Riviste r1 = new Riviste(4,"Famiglia Cristiana", LocalDate.of(2000, Month.DECEMBER, 14),50,Periodicità.SETTIMANALE);
        Riviste r2 = new Riviste(5,"Novella 2000", LocalDate.of(2004, Month.OCTOBER, 14),60,Periodicità.SETTIMANALE);
        Riviste r3 = new Riviste(6,"Focus", LocalDate.of(1998, Month.MARCH, 28),45,Periodicità.MENSILE);
        Riviste r4 = new Riviste(7,"Maghi e magie", LocalDate.of(1898, Month.FEBRUARY, 28),70,Periodicità.SEMESTRALE);

        addToArchivio(archivio,l1);
        addToArchivio(archivio,l2);
        addToArchivio(archivio,l3);
        addToArchivio(archivio,r1);
        addToArchivio(archivio,r2);
        addToArchivio(archivio,r3);
        addToArchivio(archivio,r4);
        addToArchivio(archivio,l1);



        printList(archivio);
        removeByIsbn(archivio,8);
        printList(archivio);

        ElementiBiblioteca e1 = searchByIsbn(archivio, 0);
        ElementiBiblioteca e2 = searchByIsbn(archivio, 2);


        List<ElementiBiblioteca> e3 = searchByYear(archivio, "1958");
        List<ElementiBiblioteca> e4 = searchByAuthor(archivio, "Barababa");

        try {
            salvaSuDisco(archivio);
        } catch (IOException e){
            errorLogger.error(String.valueOf(e));
        }

        try {
           ArrayList<ElementiBiblioteca> a1 =  leggiDaDisco();
           printList(a1);
        } catch (IOException e){
            errorLogger.error(String.valueOf(e));
        }
    }


    //metodo per l'eliminazione dall'archivio tramite isbn
    public static void removeByIsbn(List<ElementiBiblioteca> lista, long isbn){
        try {
            if (lista.stream().anyMatch(el -> el.getISBN() == isbn)){
                lista.removeIf(el -> el.getISBN() == isbn);
                System.out.println("Elemento con ISBN: " + isbn + " eliminato con successo!");
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException e){
            errorLogger.error("Elemento con ISBN: " + isbn + " non trovato!");
        }
    }

    public static void printList(List<ElementiBiblioteca> lista){
       lista.stream().forEach(el -> System.out.println(el));
    }

    public static ElementiBiblioteca searchByIsbn (List<ElementiBiblioteca> lista, long isbn){
        try {
            return lista.stream()
                    .filter(e -> e.getISBN() == isbn)
                    .findAny()
                    .orElseThrow(() -> new SearchExecption("Elemento con ISBN " + isbn + " non trovato"));
        } catch (SearchExecption e){
            errorLogger.error(String.valueOf(e));
        return null;
        }
    }
    public static List<ElementiBiblioteca> searchByYear (List<ElementiBiblioteca> lista, String anno){
        LocalDate date = LocalDate.of(Integer.parseInt(anno),01,01);
        try {
            List<ElementiBiblioteca> list = lista.stream().filter(e -> e.getAnnoPubblicazione().getYear() == date.getYear()).toList();
            list.stream().findAny().orElseThrow(()-> new SearchExecption("Nessun elemento con Anno " + anno + " trovato"));
            return list;
        } catch (SearchExecption e){
            errorLogger.error(String.valueOf(e));
            return null;
        }
    }

    public static List<ElementiBiblioteca> searchByAuthor (List<ElementiBiblioteca> lista, String autore){
       try {
           List<ElementiBiblioteca> list = lista.stream().filter(e -> e instanceof Libri && ((Libri) e).getAutore().toLowerCase() == autore.toLowerCase()).toList();
           list.stream().findAny().orElseThrow(()-> new SearchExecption("Nessun elemento con Autore " + autore + " trovato"));
           return list;
       } catch (SearchExecption e){
           errorLogger.error(String.valueOf(e));
           return null;
       }
    }

    //metodo di scrittura su file per creare l'archivio
    public static void salvaSuDisco (List<ElementiBiblioteca> lista) throws IOException {
        File file = new File("biblioteca/archivio.txt");
        String stringa = lista.stream()
                .map (s ->{
                    if ( s instanceof Libri){
                       return "Libro :" + "@"+ s.getISBN() + "@" + s.getTitolo() + "@" + s.getAnnoPubblicazione() + "@" + s.getnPagine() + "@" + ((Libri) s).getAutore() + "@" + ((Libri) s).getGenere();
                    } else if ( s instanceof Riviste){
                        return "Rivista :"+ "@" +  s.getISBN() + "@" + s.getTitolo() + "@" + s.getAnnoPubblicazione() + "@" + s.getnPagine() + "@" + ((Riviste) s).getPeriodicità();
                    } else {
                        return s.getISBN() + "@" + s.getTitolo() + "@" + s.getAnnoPubblicazione() + "@" + s.getnPagine();
                    }
                })
                .collect(Collectors.joining("#"));
        FileUtils.writeStringToFile(file, stringa,Charset.defaultCharset());
    }


    //metodo di lettura da file per creare l'archivio
    public static ArrayList<ElementiBiblioteca> leggiDaDisco () throws IOException {
        File file = new File("biblioteca/archivio.txt");
        String string = FileUtils.readFileToString(file, Charset.defaultCharset());
        String[] array = string.split("#");
        ArrayList<ElementiBiblioteca> archivio = Arrays.stream(array)
                .map(s -> {
                    String[] stringDetails = s.split("@");
                    if (stringDetails[0].equals("Libro :") ){
                        Libri l = new Libri(
                                 Long.parseLong(stringDetails[1])
                                ,stringDetails[2]
                                ,LocalDate.parse(stringDetails[3])
                                ,Integer.parseInt(stringDetails[4])
                                ,stringDetails[5]
                                ,stringDetails[6]
                                );
                        return l;
                    } else if (stringDetails[0].equals("Rivista :") ) {
                        Riviste r = new Riviste(
                                Long.parseLong(stringDetails[1])
                                ,stringDetails[2]
                                ,LocalDate.parse(stringDetails[3])
                                ,Integer.parseInt(stringDetails[4])
                                ,Periodicità.valueOf(stringDetails[5])
                        );
                       return r;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toCollection(ArrayList::new));
        return archivio;
    };

    //aggiunta in archivio con controllo su ISBN se è già esistente
    public static void addToArchivio(List<ElementiBiblioteca> lista, ElementiBiblioteca element) {
        if (lista.stream().map(e -> e.getISBN()).anyMatch(el -> el == element.getISBN())){
            System.out.println();
            errorLogger.error("L'archivio ha già l'elemento : " + element);
        } else {
            lista.add(element);
            infoLogger.info("Aggiunto elemento " + element);
        }
    }
    }

