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

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        checkNull(points);
        Point[] p = points.clone();
        Arrays.sort(p);
        checkDuplicates(p);
        segments = new ArrayList<>();
        for (int i = 0; i < p.length; i++) {
            for (int j = i + 1; j < p.length; j++) {
                double s1 = p[i].slopeTo(p[j]);
                for (int k = j + 1; k < p.length; k++) {
                    double s2 = p[i].slopeTo(p[k]);
                    if (s1 == s2) {
                        for (int h = k + 1; h < p.length; h++) {
                            double s3 = p[i].slopeTo(p[h]);
                            if (s1 == s3) {
                                segments.add(new LineSegment(p[i], p[h]));
                            }
                        }
                    }
                }
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
            throw new IllegalArgumentException();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
