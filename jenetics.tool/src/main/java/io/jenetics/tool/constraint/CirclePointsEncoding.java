package io.jenetics.tool.constraint;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

import io.jenetics.prngine.LCG64ShiftRandom;

import io.jenetics.tool.trial.Gnuplot;
import io.jenetics.util.ISeq;

public class CirclePointsEncoding {
	public static void main(final String[] args) throws IOException {
		final String base = "/home/fwilhelm/Workspace/Development/Projects/" +
			"Jenetics/jenetics.tool/src/main/resources/io/jenetics/tool/constraint";

		final Path data = Paths.get(base, "circle_points_encoding.dat");
		final Path output = Paths.get(base, "circle_points_encoding.svg");

		final Random random = new LCG64ShiftRandom();

		final ISeq<double[]> points = Stream.generate(() -> point(random))
			.limit(2000)
			.collect(ISeq.toISeq());

		final StringBuilder out = new StringBuilder();
		out.append("#x y\n");
		points.forEach(p -> {
			out.append(p[0]);
			out.append(" ");
			out.append(p[1]);
			out.append("\n");
		});

		Files.write(data, out.toString().getBytes());
		final Gnuplot gnuplot = new Gnuplot(Paths.get(base, "circle_points_retry.gp"));
		gnuplot.create(data, output);
	}

	private static double[] point(final Random random) {
		final double r = random.nextDouble();
		final double a = random.nextDouble()*2*PI;

		return new double[]{r*cos(a) + 1, r*sin(a) + 1};
	}


}
