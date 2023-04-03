import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class GeradorDeFigurinha {
    
    public void cria(InputStream inputStream, String nomeArquivo) throws IOException {
        // Leitura da imagem
        // InputStream inputStream = 
        //              new FileInputStream(new File("entrada/filme-maior.jpg"));
        // InputStream inputStream = 
        //              new URL("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@.jpg")
        //                  .openStream();
        BufferedImage original = ImageIO.read(inputStream);

        // Criar nova imagem translucida na memoria com espaco extra para o texto
        int largura = original.getWidth();
        int altura  = original.getHeight();
        int alturaTotal = altura + (int)(altura * 0.2);     // Ja com o espaco extra

        BufferedImage figurinha = new BufferedImage(largura, alturaTotal, BufferedImage.TRANSLUCENT);

        // Copiar a imagem original para a figurinha (na memoria)
        Graphics2D graphics = (Graphics2D) figurinha.getGraphics();
        graphics.drawImage(original, 0, 0, null);

        // Definir a fonte do texto da figurinha
        int fontSize = (int)(altura * 0.05);
        var fonte = new Font("Impact", Font.BOLD, fontSize);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        // Escrever o texto na figurinha
        String texto = "TOPZERA";

        FontMetrics fontMetrics  = graphics.getFontMetrics();
        Rectangle2D stringBounds = fontMetrics.getStringBounds(texto, graphics);
        int larguraTexto  = (int) stringBounds.getWidth();
        int alturaTexto   = (int) stringBounds.getHeight();
        int posicaoTextoX = (largura - larguraTexto) / 2;
        int posicaoTextoY = (alturaTotal - (alturaTotal - altura) / 2) + (alturaTexto / 2);

        graphics.drawString(texto, posicaoTextoX, posicaoTextoY);

        // Escrever a figurinha em um novo arquivo
        var diretorio = new File("figurinhas");

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        var arquivoSaida = new File("figurinhas", nomeArquivo);
        ImageIO.write(figurinha, "png", arquivoSaida);
    }
}