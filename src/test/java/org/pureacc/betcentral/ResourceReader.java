package org.pureacc.betcentral;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

public final class ResourceReader {
	public static String asString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}