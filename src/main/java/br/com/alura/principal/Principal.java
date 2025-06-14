package br.com.alura.principal;

import br.com.alura.model.DadosEpisodio;
import br.com.alura.model.DadosSerie;
import br.com.alura.model.DadosTemporada;
import br.com.alura.service.ConsumoApi;
import br.com.alura.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private Scanner leitura = new Scanner(System.in);

//    Variável que não serão alteradas -> constantes -> nome sempre em UPPERCASE
//    COMO OS VALORES SÃO CONSTANTES, DEVEM SER ATRIBUÍDOS
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=a8d58d21";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

//        Trazer todos os episódios da temporada
		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dados.totalTemporadas() ; i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);

		}
		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++) {
////            Pega cada episódio da temporada
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
////            Pega o título de cada episódio
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());

//            }
//        }
//        FUNÇÃO LÂMBDA -> TUDO QUE ESTÁ COMENTADO ACIMA EM UMA LINHA
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }

}
