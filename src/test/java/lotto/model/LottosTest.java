package lotto.model;

import lotto.mock.number_generator.RealRandomNumberGenerator;
import lotto.mock.number_generator.SequentialRandomNumberGenerator;
import lotto.model.number.LottoNumber;
import lotto.model.number.LottoNumbers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Lottos 테스트")
public class LottosTest {

    private static final int LOTTO_NUMBER_SIZE = 6;
    private final int WINNING_LOTTO_NUMBER_SIZE = 6;
    private final RealRandomNumberGenerator realRandomNumberGenerator = new RealRandomNumberGenerator();
    private final SequentialRandomNumberGenerator sequentialRandomNumberGenerator =
            new SequentialRandomNumberGenerator();

    public static List<Lotto> getLottos(Lottos lottos) {
        try {
            Field field = Lottos.class.getDeclaredField("lottos");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            List<Lotto> filedValue = (List<Lotto>) field.get(lottos);

            return filedValue;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Lottos 객체의 lottos 필드 접근에 실패했습니다.");
        }
    }

    @BeforeEach
    void setUp() {
        sequentialRandomNumberGenerator.setSizeWillBeGenerated(LOTTO_NUMBER_SIZE);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("사이즈만큼의 로또를 랜덤으로 생성한다.")
    void 사이즈만큼의_로또를_랜덤으로_생성한다(int lottoCount) {

        // given
        int expected = lottoCount;

        // when
        int actual = getLottos(Lottos.generateBy(realRandomNumberGenerator, lottoCount)).size();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("로또 당첨 통계를 반환한다.")
    void 로또_당첨_통계를_반환한다(int lottoCount) {

        // given
        Lottos lottos = Lottos.generateBy(realRandomNumberGenerator, lottoCount);

        LottoNumbers lottoNumbers = LottoNumbers.generateBy(WINNING_LOTTO_NUMBER_SIZE, sequentialRandomNumberGenerator);
        LottoNumber bonusNumber = LottoNumber.from(WINNING_LOTTO_NUMBER_SIZE + 1);

        WinningLotto winningLotto1 = new WinningLotto(lottoNumbers, bonusNumber);

        List<Score> expected = getLottos(lottos).stream()
                .map(lotto -> Score.calculateScore(lotto, winningLotto1))
                .toList();


        // when
        List<Score> actual = lottos.calculateScore(winningLotto1);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30, 40})
    @DisplayName("로또들의 번호를 반환한다.")
    void 로또들의_번호를_반환한다(int lottoCount) {

        // given
        Lottos generatedLottos = Lottos.generateBy(sequentialRandomNumberGenerator, lottoCount);

        // when
        List<LottoNumbers> expected = getLottos(generatedLottos).stream()
                .map(Lotto::getLottoNumbers)
                .toList();

        List<LottoNumbers> actual = generatedLottos.getAllLottoNumbers();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
