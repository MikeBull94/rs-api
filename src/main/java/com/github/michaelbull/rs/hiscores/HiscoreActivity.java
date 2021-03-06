package com.github.michaelbull.rs.hiscores;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import org.apache.commons.csv.CSVRecord;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Represents a player's rank in an activity on the {@link Hiscores}.
 */
public final class HiscoreActivity {

	/**
	 * Creates a {@link HiscoreActivity} from a {@link CSVRecord}.
	 * @param record The record.
	 * @return The {@link HiscoreActivity} or {@link Optional#empty()} if the record was invalid.
	 */
	public static Optional<HiscoreActivity> fromCsv(CSVRecord record) {
		if (record.size() < 2) {
			return Optional.empty();
		}

		Integer rank = Ints.tryParse(record.get(0));
		if (rank == null) {
			return Optional.empty();
		}

		Integer score = Ints.tryParse(record.get(1));
		if (score == null) {
			return Optional.empty();
		}

		return Optional.of(new HiscoreActivity(rank, score));
	}

	/**
	 * The rank the player has in the activity.
	 */
	private final int rank;

	/**
	 * The score the player has in the activity.
	 */
	private final int score;

	/**
	 * Creates a new {@link HiscoreActivity}.
	 * @param rank The rank the player has in the activity.
	 * @param score The score the player has in the activity.
	 */
	public HiscoreActivity(int rank, int score) {
		Preconditions.checkArgument(rank == -1 || rank > 0, "Rank must be either -1 (unranked) or positive.");
		Preconditions.checkArgument(score >= -1, "Score must be either -1 (unranked) or non-negative.");
		this.rank = rank;
		this.score = score;
	}

	/**
	 * Creates a new {@link HiscoreActivity} from a {@link CSVRecord}.
	 * @param record The {@link CSVRecord}.
	 * @deprecated Use {@link #fromCsv(CSVRecord)}.
	 */
	@Deprecated
	public HiscoreActivity(CSVRecord record) {
		Preconditions.checkNotNull(record);
		this.rank = Integer.parseInt(record.get(0));
		this.score = Integer.parseInt(record.get(1));
	}

	/**
	 * Gets the rank the player has in the activity.
	 * @return An {@link OptionalInt} containing the player's rank, or {@link OptionalInt#empty()} if the player is unranked for this activity.
	 */
	public OptionalInt getRank() {
		return rank == -1 ? OptionalInt.empty() : OptionalInt.of(rank);
	}

	/**
	 * Gets the score the player has in the activity.
	 * @return An {@link OptionalInt} containing the player's score, or {@link OptionalInt#empty()} if the player is unranked for this activity.
	 */
	public OptionalInt getScore() {
		return score == -1 ? OptionalInt.empty() : OptionalInt.of(score);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HiscoreActivity that = (HiscoreActivity) o;
		return rank == that.rank
			&& score == that.score;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank, score);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("rank", getRank().orElse(-1))
			.add("score", getScore().orElse(-1))
			.toString();
	}
}
