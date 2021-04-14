package com.boorad.sps;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.when;

public class TestUtils {

  // sample data from the exercise
  public static BufferedReader getReader() throws IOException {
    BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
    when(bufferedReader.readLine())
      .thenReturn("data: {\"device\":\"xbox_one\",\"sev\":\"error\",\"title\":\"orange is the new black\",\"country\":\"IND\",\"time\":1515445354624}")
      .thenReturn("data: {\"device\":\"xbox_one\",\"sev\":\"error\",\"title\":\"orange is the new black\",\"country\":\"IND\",\"time\":1515445354624}")
      .thenReturn("data: {\"device\":\"xbox_one_s\",\"sev\":\"success\",\"title\":\"cuervos\",\"country\":\"BR\",\"time\":1515445354624}")
      .thenReturn("data: {\"device\":\"ios\",\"sev\":\"success\",\"title\":\"daredevil\",\"country\":\"BR\",\"time\":1515445354624}")
      .thenReturn("data: {\"device\":\"ios\",\"sev\":\"success\",\"title\":\"daredevil\",\"country\":\"BR\",\"time\":1515445354625}")
      .thenReturn("data: {\"device\":\"xbox_360\",\"sev\":\"success\",\"title\":\"narcos\",\"country\":\"IND\",\"time\":1515445354625}");
    return bufferedReader;
  }

}
