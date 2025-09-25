package sistemaOperacionalFase2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulador {
	private AlgoritmoAlocacao algoritmo;
	private GeradorDeProcessos gerador = new GeradorDeProcessos();
	private Memoria memoria = new Memoria(1000);
	private Random random = new Random();

	private int processosGerados = 0;
	private int somaTamanhos = 0;
	private int processosDescartados = 0;
	private int somaOcupacao = 0;

	public Simulador(AlgoritmoAlocacao algoritmo) {
		this.algoritmo = algoritmo;
	}

	public Resultado executar() {
		List<Processo> ativos = new ArrayList<>();

		for (int tempo = 0; tempo < 100; tempo++) {
			for (int i = 0; i < 2; i++) {
				Processo p = gerador.gerar();
				processosGerados++;
				somaTamanhos += p.getTamanho();

				if (algoritmo.alocar(memoria, p)) {
					ativos.add(p);
				} else {
					processosDescartados++;
				}
			}

			int remover = random.nextInt(2) + 1;
			for (int i = 0; i < remover && !ativos.isEmpty(); i++) {
				Processo p = ativos.remove(random.nextInt(ativos.size()));
				memoria.liberar(p);
			}

			somaOcupacao += memoria.ocupacao();
		}

		double mediaTamanho = (double) somaTamanhos / processosGerados;
		double ocupacaoMedia = (double) somaOcupacao / 100
				/ memoria.getTamanho() * 100;
		double taxaDescarte = (double) processosDescartados / processosGerados
				* 100;

		return new Resultado(mediaTamanho, ocupacaoMedia, taxaDescarte);
	}
}
