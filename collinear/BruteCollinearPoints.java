/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {

        if (points == null || anyNull(points) || nonDistinct(points)) {
            throw new IllegalArgumentException();
        }

        lineSegments = new ArrayList<>();

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        if (isCollinear(points[i], points[j], points[k], points[m])) {
                            final LineSegment lineSegment =
                                    createLineSegment(points[i], points[j], points[k], points[m]);
                            lineSegments.add(lineSegment);
                        }
                    }
                }
            }
        }
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

    private LineSegment createLineSegment(Point point, Point point1, Point point2, Point point3) {
        Point[] points = {point, point1, point2, point3};
        Arrays.sort(points);
        return new LineSegment(points[0], points[3]);
    }

    private boolean isCollinear(Point point, Point point1, Point point2, Point point3) {
        return point.slopeTo(point1) == point.slopeTo(point2)
                && point.slopeTo(point1) == point.slopeTo(point3);
    }
}
