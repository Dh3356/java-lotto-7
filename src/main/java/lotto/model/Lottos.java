package lotto.model;


import lotto.model.number.LottoNumbers;
import lotto.model.number_generator.RandomNumberGenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lottos {

    private final List<Lotto> lottos;

    private Lottos(List<Lotto> lottos) {
        this.lottos = lottos;
    }

    public static Lottos generateBy(RandomNumberGenerator randomNumberGenerator, int size) {
        return new Lottos(IntStream.range(0, size)
                .mapToObj(count -> Lotto.generateBy(randomNumberGenerator))
                .toList());
    }

    public List<Score> calculateScore(WinningLotto winningLotto) {
        return lottos.stream()
                .map(lotto -> Score.calculateScore(lotto, winningLotto))
                .collect(Collectors.toList());
    }

    public List<LottoNumbers> getAllLottoNumbers() {
        return lottos.stream()
                .map(Lotto::getNumbers)
                .toList();
    }
}
