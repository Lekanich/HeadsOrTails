package lekanich;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class HeadsOrTailsActonTest {
	public static final int TEST_FLIP_COIN_COUNT = 5_000_000;

	@Test
	public void checkRightnessOfFlipCoin() {
		Map<HeadsOrTailsAction.Coin, Long> result = LongStream.range(0, TEST_FLIP_COIN_COUNT)
				.mapToObj(i -> HeadsOrTailsAction.flipCoin())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		Long headsAmount = result.get(HeadsOrTailsAction.Coin.HEAD);
		Long tailsAmount = result.get(HeadsOrTailsAction.Coin.TAIL);
		long delta = Math.abs(headsAmount - tailsAmount);

		long thresholdValue = (long) (TEST_FLIP_COIN_COUNT * 0.002);
		MatcherAssert.assertThat("Delta between head and tails is more than expected", delta, Matchers.lessThan(thresholdValue));
	}
}
