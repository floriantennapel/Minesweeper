package no.uib.inf101.grid;

// Les om records her: https://inf101.ii.uib.no/notat/mutabilitet/#record

/**
 * A GridCell contains a CellPosition and a Color.
 *
 * @param pos  the position of the cell
 * @param elem        the elem of the cell
 */
public record GridCell<T>(CellPosition pos, T elem) { }
