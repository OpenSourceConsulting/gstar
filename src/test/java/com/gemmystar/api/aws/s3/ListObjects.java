package com.gemmystar.api.aws.s3;

import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class ListObjects
{
    public static void main(String[] args)
    {
    	/*
        final String USAGE = "\n" +
            "To run this example, supply the name of a bucket to list!\n" +
            "\n" +
            "Ex: ListObjects <bucket-name>\n";

        if (args.length < 1)
        {
            System.out.println(USAGE);
            System.exit(1);
        }

        String bucket_name = args[0];
        */
    	String bucket_name = "gstar-prd";

        System.out.format("Objects in S3 bucket %s:\n", bucket_name);
        final AmazonS3 s3 = new AmazonS3Client();
        ObjectListing ol = s3.listObjects(bucket_name);
        List<S3ObjectSummary> objects = ol.getObjectSummaries();
        for (S3ObjectSummary os: objects)
        {
            System.out.println("* " + os.getKey()+ "  " + "(size = " + os.getSize() + ")");
        }
    }
}