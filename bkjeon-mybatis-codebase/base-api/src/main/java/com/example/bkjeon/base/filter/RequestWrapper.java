package com.example.bkjeon.base.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

/**
 * 요청된 HTTP 접근
 * [HttpServletRequestWrapper]
 * Servlet 관련 인터페이스 제공
 */
public class RequestWrapper extends HttpServletRequestWrapper {

	private final byte[] cachedInputStream;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		InputStream requestInputStream = request.getInputStream();
		this.cachedInputStream = StreamUtils.copyToByteArray(requestInputStream);
	}

	/**
	 * binary data로 Request Body 정보를 담은 ServletInputStream(inputstream)을 반환한다.
	 * @return
	 */
	@Override
	public ServletInputStream getInputStream() {
		return new ServletInputStream() {
			private final InputStream cachedBodyInputStream = new ByteArrayInputStream(cachedInputStream);

			@Override
			public boolean isFinished() {
				try {
					// 더 이상 읽을 byte 가 없을 때 return
					return cachedBodyInputStream.available() == 0;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				throw new UnsupportedOperationException();
			}

			@Override
			public int read() throws IOException {
				return cachedBodyInputStream.read();
			}
		};
	}

}