package ru.stqa.pft.github;

import com.google.common.collect.ImmutableBiMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("ebe04dfd9d6a246893615ca8cdb3995d5d09de73");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("Antonius1", "java_pft")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableBiMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
