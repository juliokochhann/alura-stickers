import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {        
        // API endpoint
        API api = API.IMDB_TOP_MOVIES;

        String url = api.getUrl();
        ExtratorDeConteudo extrator = api.getExtrator();

        var  client = new ClienteHttp();
        String json = client.getJSON(url);
        
        // Exibir e manipular os dados 
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var gerador = new GeradorDeFigurinha();

        for (int i = 0; i < 3; i++) {
            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = conteudo.getTitulo().replace(":", "") + ".png";

            gerador.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.getTitulo() + "\n");
        }
    }
}
