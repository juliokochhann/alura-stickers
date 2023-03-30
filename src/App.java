import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        String imdb_url = "https://www.imdb.com/title/";
        // API endpoint
        String url;
        // url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
        // url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";

        URI uri = URI.create(url);
        var cliente = HttpClient.newHttpClient();   // var - tipo inferido automaticamente
        var request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = cliente.send(request, BodyHandlers.ofString());

        String body = response.body();
        
        // Extrair os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir os filmes
        String title, year, poster, imDbRating, imDbLink;

        for (Map<String, String> filme : listaDeFilmes) {
            title  = filme.get("title");
            year   = filme.get("year");
            poster = filme.get("image");
            imDbRating = filme.get("imDbRating");
            imDbLink   = imdb_url + filme.get("id");

            int stars = (int) Double.parseDouble(imDbRating);

            System.out.print("\u001b[1mTitle :\u001b[m " + title + " \u001b[m");
            System.out.println("[ " + year + " ]");

            System.out.print("\u001b[33m");
            int n;
            for (n = 1; n <= stars; n++) {
                System.out.print(" ★ ");
            }
            for (; n <= 10; n++) {
                System.out.print(" ☆ ");
            }
            System.out.println("\u001b[m");

            System.out.println("\u001b[1mRating:\u001b[m " + imDbRating);
            System.out.println("\u001b[1mPoster:\u001b[m " + poster);
            System.out.println("\u001b[1mIMDb  :\u001b[m " + imDbLink + "\n");
        }
    }
}
