package sistemaOperacionalFase2;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Memoria {
	private List<BlocoMemoria> blocos = new LinkedList<>();
	private int tamanho;

	public Memoria(int tamanho) {
		this.tamanho = tamanho;
		blocos.add(new BlocoMemoria(0, tamanho, true));
	}

	public List<BlocoMemoria> getBlocos() {
		return blocos;
	}

	public void liberar(Processo p) {
		for (BlocoMemoria b : blocos) {
			if (!b.livre && b.processo.getId() == p.getId()) {
				b.livre = true;
				b.processo = null;
				merge();
				break;
			}
		}
	}

	private void merge() {
		ListIterator<BlocoMemoria> iterator = blocos.listIterator();
		while (iterator.hasNext()) {
			BlocoMemoria atual = iterator.next();
			if (iterator.hasNext()) {
				BlocoMemoria prox = iterator.next();
				if (atual.livre && prox.livre) {
					atual.tamanho += prox.tamanho;
					iterator.remove();
					iterator.previous();
				} else {
					iterator.previous();
				}
			}
		}
	}

	public int ocupacao() {
		int usado = 0;
		for (BlocoMemoria b : blocos) {
			if (!b.livre)
				usado += b.tamanho;
		}
		return usado;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void dividirBloco(BlocoMemoria b, Processo p) {
		int index = blocos.indexOf(b);

		if (b.tamanho > p.getTamanho()) {
			BlocoMemoria novo = new BlocoMemoria(b.inicio + p.getTamanho(),
					b.tamanho - p.getTamanho(), true);

			b.tamanho = p.getTamanho();
			b.livre = false;
			b.processo = p;

			blocos.add(index + 1, novo);
		} else {
			b.livre = false;
			b.processo = p;
		}
	}
}
