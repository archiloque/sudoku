package net.archiloque.sukoku;

import org.testng.annotations.Test;

/**
 * © Julien Kirch 2013 - Licensed under MIT license
 */
public class GridTest {

    @Test
    public void testToString() {
        System.out.println(new Grid().toString());
    }

    @Test
    public void testGrid1() {
        Grid grid = new Grid(" 469 357 2  178  6          9     5   13 49   6     1          7  412  3 147 982 ");
        assert grid.toString().equals(
                "846923571\n" +
                        "259178436\n" +
                        "137645289\n" +
                        "398261754\n" +
                        "571384962\n" +
                        "462597318\n" +
                        "923856147\n" +
                        "785412693\n" +
                        "614739825\n");
    }

    @Test
    public void testGrid2() {
        Grid grid = new Grid("6 74  1  4  367  9 5   8    6    7   45   21   2    6    9   4 5  283  1  6  15 2");
        assert grid.toString().equals(
                "687495123\n" +
                        "421367859\n" +
                        "953128476\n" +
                        "869512734\n" +
                        "745639218\n" +
                        "132874965\n" +
                        "218956347\n" +
                        "574283691\n" +
                        "396741582\n"
        );
    }

    @Test
    public void testGrid3() {
        Grid grid = new Grid("5 9 7 8 6  8 3 1  6       2 8 4 6 9           3 7 5 2 3       4  7 6 5  1 4 5 2 3");
        assert grid.toString().equals(
                "549271836\n" +
                        "278639145\n" +
                        "613548972\n" +
                        "785426391\n" +
                        "962183457\n" +
                        "431795628\n" +
                        "356912784\n" +
                        "827364519\n" +
                        "194857263\n");
    }
}
