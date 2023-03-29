import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
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
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("\u001b[1mTitle :\u001b[m " + filme.get("title") + "\u001b[m");

            String imDbRating = filme.get("imDbRating");
            int rating = (int) Double.parseDouble(imDbRating);

            System.out.print("\u001b[1mRating: \u001b[m\u001b[7m " + imDbRating + " \u001b[m\u001b[33m");

            for (int n = 0; n < rating; n++) {
                System.out.print(" ★ ");
            }

            System.out.println("\n\u001b[m\u001b[1mPoster:\u001b[m " + filme.get("image"));
            System.out.println();
        }
    }
}
