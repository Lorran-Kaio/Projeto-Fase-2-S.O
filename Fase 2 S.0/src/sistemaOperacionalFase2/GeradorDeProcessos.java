package sistemaOperacionalFase2;

import java.util.concurrent.ThreadLocalRandom;

class GeradorDeProcessos {
	public Processo gerar() {
		int tamanho = ThreadLocalRandom.current().nextInt(10, 51);
		return new Processo(tamanho);
	}
}
