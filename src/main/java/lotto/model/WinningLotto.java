package lotto.model;

import java.util.List;

public class WinningLotto {

    private static final int NUMBER_SIZE = 6;

    private final List<LottoNumber> numbers;
    private final LottoNumber bonusNumber;

    public WinningLotto(List<LottoNumber> numbers, LottoNumber bonusNumber) {
        validate(numbers, bonusNumber);
        this.numbers = numbers;
        this.bonusNumber = bonusNumber;
    }

    public int getMatchCount(Lotto lotto) {
        return (int) lotto.getNumbers().stream().filter(numbers::contains).count();
    }

    public boolean isBonusNumberMatches(Lotto lotto) {
        return lotto.getNumbers().contains(bonusNumber);
    }

    private void validate(List<LottoNumber> numbers, LottoNumber bonusNumber) {
        validateNumbers(numbers);
        validateBonusNumber(numbers, bonusNumber);
    }

    private void validateNumbers(List<LottoNumber> numbers) {
        if (numbers.size() != NUMBER_SIZE) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        if (numbers.stream().distinct().count() != NUMBER_SIZE) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되지 않아야 합니다.");
        }
    }

    private void validateBonusNumber(List<LottoNumber> numbers, LottoNumber bonusNumber) {
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호가 당첨 번호에 포함되서는 안됩니다.");
        }
    }
}
