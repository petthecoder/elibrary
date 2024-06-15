package com.boskman.elibrary.utils;

import com.boskman.elibrary.author.Author;
import com.boskman.elibrary.author.AuthorRequest;
import com.boskman.elibrary.author.AuthorService;
import com.boskman.elibrary.book.Book;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookScrapingService {

    private final ScrapingParserService parser;
    private final AuthorService authorService;

    public Book createBookByIsbn(String isbn) {
        String url = "https://www.todostuslibros.com/busquedas?keyword=" + isbn;

        Document document = null;
        try {
            document = Jsoup.connect(url).get();

            String dataSheet = document.select("#fichaTecnica dl.datos-tecnicos").text();
            String title = document.select("#info h1.title").first().text();
            String image = document.select("#info img.portada").attr("src");
            String synopsis = document.select("#synopsis div#collapseSynopsis").html();

            String publicationYear = parser.parseField(dataSheet, "Fecha publicación : (\\d{2}-\\d{2}-(\\d{4}))");
            String genre = parser.parseField(dataSheet, "Materias: (.*)");
            String format = parser.parseField(dataSheet, "Formato: (.*) País de publicación");

            String pages = parser.parseField(dataSheet, "Nº páginas: (\\d+)");
            String publisher = parser.parseField(dataSheet, "Editorial: (.*) Autor/a");
            String author = parser.parseField(dataSheet, "Autor/a: (.*) Traductor/a");

            Set<Author> authors = Arrays.stream(author.split(" / "))
                    .map(this::createAuthor)
                    .collect(Collectors.toSet());

            return Book.builder()
                    .title(title)
                    .synopsis(synopsis)
                    .publicationYear(parser.parsePublicationYear(publicationYear))
                    .isbn(isbn)
                    .pages(Integer.parseInt(pages))
                    .image(image)
                    .format(format)
                    .authors(authors)
                    .genre(genre)
                    .publisher(publisher)
                    .build();

        } catch (IOException e) {
            throw new ScrapingCreationException("Unable to createThe information from the isbn", e);
        }
    }

    public Author createAuthor(String scrapedName) {
        try {

            String[] parts = scrapedName.split(", ");
            String name = parts[1] + " " + parts[0];

            String query = "https://www.google.com/search?q=" + name.replace(" ", "+") + "&tbm=isch";
            Document document = Jsoup.connect(query).userAgent("Mozilla/5.0").get();

            Elements images = document.select("img");
            String photoUrl = null;
            for (Element image : images) {
                String src = image.attr("src");
                if (src != null && src.startsWith("http")) {
                    photoUrl = src;
                    break;
                }
            }

            return authorService.createAuthor(AuthorRequest.builder()
                    .name(name)
                    .photoUrl(photoUrl)
                    .build());
        } catch (Exception e) {
            throw new ScrapingCreationException("Unable to create the author from the scraped name", e);
        }
    }
}


