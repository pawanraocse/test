package com.practise.test.tiptop;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TipTopCallingClass {

    private Path videoFile;

    public static void main(String[] args) {
        TipTopCallingClass topCallingClass = new TipTopCallingClass();
        topCallingClass.enter();
    }

    private void enter() {
        TipTopApiClient tipTopApiClient = new TipTopApiClient();
        try {
            long jobId = tipTopApiClient.uploadFile(videoFile);
            Future<TipTopJobDetails> statusCheckFuture = tipTopApiClient.executeAsyncApiToStatusCheck(jobId);

            // Wait for status check to complete
            TipTopJobDetails jobDetails = statusCheckFuture.get();
            TipTopJobStatus jobStatus = TipTopJobStatus.valueOf(jobDetails.getJobStatus().toUpperCase());

            switch (jobStatus) {
                case FINAL:
                    // If job status is "FINAL", initiate asynchronous download of job results
                    Future<Void> downloadFuture = tipTopApiClient.downloadJobResultAsync(jobId, "<path-to-save-job-results>");
                    downloadFuture.get();

                    System.out.println("Job completed successfully.");
                    break;
                case QUEUED:
                case INPROGRESS:
                    System.out.println("Job is still in progress.");
                    break;
                case FAILED:
                    System.out.println("Job failed: " + jobDetails.getErrorMessage());
                    break;
                default:
                    System.out.println("Unknown job status: " + jobDetails.getJobStatus());
                    break;
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setVideoFile(final Path videoFile) {
        this.videoFile = videoFile;
    }
}
