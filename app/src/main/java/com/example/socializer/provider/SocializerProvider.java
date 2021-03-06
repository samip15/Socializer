package com.example.socializer.provider;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(
        authority = SocializerProvider.AUTHORITY,
        database = SocializerDatabase.class)
public class SocializerProvider {
    public static final String AUTHORITY = "com.example.socializer.provider";

    @TableEndpoint(table = SocializerDatabase.SOCIALIZER_POSTS)
    public static class SocializerPosts {
        @ContentUri(
                path = "posts",
                type = "vnd.android.cursor.dir/posts",
                defaultSort = SocializerContract.COLUMN_DATE + "DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/posts");
    }
}
