package graphs.matchings;

import java.util.*;

// https://en.wikipedia.org/wiki/Hungarian_algorithm in O(n^2 * m)
public class Hungarian {

    // a[n][m], n <= m, sum(a[i][p[i]] -> min
    public static int minWeightPerfectMatching(int[][] a) {
        int n = a.length - 1;
        int m = a[0].length - 1;
        int[] u = new int[n + 1];
        int[] v = new int[m + 1];
        int[] p = new int[m + 1];
        int[] way = new int[m + 1];
        for (int i = 1; i <= n; ++i) {
            int[] minv = new int[m + 1];
            Arrays.fill(minv, Integer.MAX_VALUE);
            boolean[] used = new boolean[m + 1];
            p[0] = i;
            int j0 = 0;
            while (p[j0] != 0) {
                used[j0] = true;
                int i0 = p[j0];
                int delta = Integer.MAX_VALUE;
                int j1 = 0;
                for (int j = 1; j <= m; ++j)
                    if (!used[j]) {
                        int d = a[i0][j] - u[i0] - v[j];
                        if (minv[j] > d) {
                            minv[j] = d;
                            way[j] = j0;
                        }
                        if (delta > minv[j]) {
                            delta = minv[j];
                            j1 = j;
                        }
                    }
                for (int j = 0; j <= m; ++j)
                    if (used[j]) {
                        u[p[j]] += delta;
                        v[j] -= delta;
                    } else
                        minv[j] -= delta;
                j0 = j1;
            }
            while (j0 != 0) {
                int j1 = way[j0];
                p[j0] = p[j1];
                j0 = j1;
            }
        }
        int[] matching = new int[n + 1];
        for (int i = 1; i <= m; ++i)
            matching[p[i]] = i;
        return -v[0];
    }
}
