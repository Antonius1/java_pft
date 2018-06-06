package ru.stqa.pft.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;

import static org.apache.http.client.fluent.Request.Get;

public class TestBase {

  public Executor getExecutor() {
    return Executor.newInstance().auth("278bac5e81d71a7490f9adcf001a7032", "");
  }

  boolean isIssueOpen(int issueId) throws IOException {
    String json = getExecutor().execute((Request) Get("http://bugify.stqa.ru/api/issues/" + issueId + ".json")).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    JsonElement issue_array = issues.getAsJsonArray().get(0);
    String states = issue_array.getAsJsonObject().get("state_name").getAsString();
    if (states.equals("Open")) {
      return true;
    }
    return false;
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
