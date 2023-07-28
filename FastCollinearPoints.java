/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        checkNull(points);
        Point[] po = points.clone();
        Arrays.sort(po);
        checkDuplicates(po);
        segments = new ArrayList<>();

        for (int i = 0; i < po.length; i++) {
            Arrays.sort(po);
            Arrays.sort(po, po[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < po.length; last++) {
                while (last < po.length && po[p].slopeTo(po[first]) == po[p].slopeTo(po[last])) {
                    last++;
                }
                if (last - first >= 3 && po[p].compareTo(po[first]) < 0) {
                    segments.add(new LineSegment(po[p], po[last - 1]));
                }
                first = last;
            }
        }

    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    private void checkNull(Point[] p) {
        if (Arrays.asList(p).contains(null)) {
            throw new NullPointerException();
        }
    }

    private void checkDuplicates(Point[] p) {
        for (int i = 0; i < p.length - 1; i++) {
            if (p[i].compareTo(p[i + 1]) == 0) throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
