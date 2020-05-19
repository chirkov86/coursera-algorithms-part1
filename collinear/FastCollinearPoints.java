/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private static final double THRESHOLD = Math.pow(10, -9);
    private final List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {

        if (points == null || anyNull(points) || nonDistinct(points)) {
            throw new IllegalArgumentException();
        }
        lineSegments = new ArrayList<>();

        Arrays.sort(points, Point::compareTo);
        // O(N)
        for (int i = 0; i < points.length; i++) {
            Point curr = points[i];
            // O(N)
            final Point[] aux = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(aux, curr.slopeOrder()); // O(N*LogN)

            int count = 1;
            double slope = Double.NEGATIVE_INFINITY;
            int start = 0;
            for (int j = 0; j < aux.length; j++) {
                if (aux[j].equals(curr)) continue; // skip self
                if (isEqualSlope(curr.slopeTo(aux[j]), slope)) {
                    count++;
                } else {
                    if (count >= 3) {
                        final LineSegment lineSegment =
                                createLineSegment(curr, aux, start, start + count);
                        lineSegments.add(lineSegment);
                    }
                    start = j;
                    count = 1;
                }
                slope = curr.slopeTo(aux[j]);
            }
        }
    }

    private boolean isEqualSlope(double slope1, double slope2) {
        return Math.abs(slope1 - slope2) < THRESHOLD;
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(LineSegment[]::new);
    }

    private boolean nonDistinct(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].equals(points[j])) return true;
            }
        }
        return false;
    }

    private boolean anyNull(Point[] points) {
        for (Point point : points) {
            if (point == null) return true;
        }
        return false;
    }

    private LineSegment createLineSegment(Point point, Point[] aux, int lo, int hi) {
        Point[] points = new Point[hi-lo + 1];
        points[0] = point;
        for (int i = lo; i < hi; i++) {
            points[i - lo + 1] = aux[i];
        }
        Arrays.sort(points);
        return new LineSegment(points[0], points[points.length - 1]);
    }

}
