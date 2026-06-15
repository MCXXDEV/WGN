package dev.mcxxdev.wgn.build;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Blocks adult content, slurs, trademarks, and other inappropriate build requests.
 */
public final class BuildContentFilter {
	private static final Set<String> BLOCKED_TERMS = Set.of(
			// Adult / inappropriate
			"porn", "xxx", "nsfw", "nude", "naked", "sex", "hentai", "erotic", "fetish",
			// Hate / slurs (partial list — extend as needed)
			"nazi", "hitler", "swastika", "kkk",
			// Trademarks / copyrighted IP (build requests referencing protected brands)
			"disney", "mickey", "minnie", "marvel", "spiderman", "batman", "superman",
			"pokemon", "pikachu", "mario", "luigi", "zelda", "minecraft logo",
			"star wars", "darth vader", "harry potter", "hogwarts", "fortnite",
			"roblox", "among us", "coca cola", "mcdonalds", "nike", "apple logo"
	);

	private static final Pattern NON_ALPHANUM = Pattern.compile("[^a-z0-9\\s]");

	private BuildContentFilter() {}

	public static FilterResult validate(String request) {
		if (request == null || request.isBlank()) {
			return FilterResult.deny("Describe what you want to build.");
		}
		if (request.length() > 200) {
			return FilterResult.deny("Description is too long (max 200 characters).");
		}

		String normalized = NON_ALPHANUM.matcher(request.toLowerCase(Locale.ROOT)).replaceAll(" ");
		for (String term : BLOCKED_TERMS) {
			if (containsTerm(normalized, term)) {
				return FilterResult.deny("That request cannot be built. WGN does not generate adult, trademarked, or inappropriate content.");
			}
		}
		return FilterResult.ok();
	}

	private static boolean containsTerm(String normalized, String term) {
		if (term.contains(" ")) {
			return normalized.contains(term);
		}
		for (String word : normalized.split("\\s+")) {
			if (word.equals(term)) {
				return true;
			}
		}
		return normalized.contains(" " + term + " ") || normalized.startsWith(term + " ") || normalized.endsWith(" " + term);
	}

	public record FilterResult(boolean allowed, String reason) {
		public static FilterResult ok() {
			return new FilterResult(true, "");
		}

		public static FilterResult deny(String reason) {
			return new FilterResult(false, reason);
		}
	}
}
