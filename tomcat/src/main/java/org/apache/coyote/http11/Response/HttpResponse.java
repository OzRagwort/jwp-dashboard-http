package org.apache.coyote.http11.Response;

import org.apache.coyote.http11.model.Headers;
import org.apache.coyote.http11.model.Status;
import org.apache.coyote.http11.model.View;

public class HttpResponse {

    private static final String HTTP_VERSION = "HTTP/1.1";

    private StatusLine statusLine;
    private final ResponseHeader header = new ResponseHeader();
    private String responseBody;

    public HttpResponse() {
    }

    public static HttpResponse ok(final String body) {
        final HttpResponse response = new HttpResponse();
        response.statusLine = createStatusLine(Status.OK);
        response.responseBody = body;
        response.header.addHeader(Headers.CONTENT_LENGTH.getName(), String.valueOf(body.getBytes().length));

        return response;
    }

    public static HttpResponse found(final String location) {
        final HttpResponse response = new HttpResponse();
        response.statusLine = createStatusLine(Status.FOUND);
        response.header.setLocation(location);

        return response;
    }

    public static HttpResponse notFound() {
        final HttpResponse response = new HttpResponse();
        response.statusLine = createStatusLine(Status.FOUND);
        response.header.setLocation(View.NOT_FOUND.getPath());

        return response;
    }

    public static HttpResponse methodNotAllowed() {
        final HttpResponse response = new HttpResponse();
        response.statusLine = createStatusLine(Status.METHOD_NOT_ALLOWED);

        return response;
    }

    private static StatusLine createStatusLine(final Status status) {
        return new StatusLine(HTTP_VERSION, status.code(), status.message());
    }

    public String toResponse() {
        return String.join("\r\n",
                statusLine.getResponse(),
                header.getResponse(),
                "",
                responseBody);
    }

    public HttpResponse cookie(final String cookie) {
        if (cookie == null) {
            return this;
        }
        header.setCookie(cookie);
        return this;
    }

    public HttpResponse contentType(final String contentType) {
        if (contentType == null) {
            return this;
        }
        header.addContentType(contentType);
        return this;
    }
}
