package usy.aibhub.amqp.examples;

/**
 * This class represents Business Application message entity
 *
 * @author Dominik Lohniský
 * @since 08. 09. 2023
 */
public class BaMessage {

  private String headers;

  private byte[] content;

  public BaMessage(byte[] content) {
    this.content = content;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public String getHeaders() {
    return headers;
  }

  public void setHeaders(String headers) {
    this.headers = headers;
  }
}
