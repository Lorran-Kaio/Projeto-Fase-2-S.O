package sistemaOperacionalFase2;

import java.util.List;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		int repeticoes = 100;

		List<AlgoritmoAlocacao> algoritmos = Arrays.asList(new FirstFit(),
				new BestFit(), new WorstFit(), new NextFit());

		List<String> nomes = Arrays.asList("First Fit", "Best Fit",
				"Worst Fit", "Next Fit");

		for (int i = 0; i < algoritmos.size(); i++) {
			double somaTamanho = 0, somaOcupacao = 0, somaDescarte = 0;

			for (int r = 0; r < repeticoes; r++) {
				Simulador simulador = new Simulador(algoritmos.get(i));
				Resultado resultado = simulador.executar();
				somaTamanho += resultado.mediaTamanho;
				somaOcupacao += resultado.ocupacaoMedia;
				somaDescarte += resultado.taxaDescarte;
			}

			System.out.println("===== " + nomes.get(i) + " =====");
			System.out.printf("Média tamanho dos processos: %.2f%n", somaTamanho
					/ repeticoes);
			System.out.printf("Ocupação média da memória: %.2f%%%n", somaOcupacao
					/ repeticoes);
			System.out.printf("Taxa de descarte: %.2f%%%n%n", somaDescarte
					/ repeticoes);
		}
	}
}
