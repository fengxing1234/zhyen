package com.zhyen.android.test.test_provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class TestPictureContract {

    //标准的构成是应用的包名+自定义Provider名子
    public static final String AUTHORITY = "com.zhyen.android.TestPictureProvider";

    //Picture 表
    public static final class IPicture implements BaseColumns {
        //ID，TITLE，AUTHOR是表的列名
        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "_title";
        public static final String AUTHOR = "_author";

        //CONTENT_URI：标准构成为content:// + AUTHORITY + 表名 ， 这个我们给表名取名为ipicture
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ipicture");
        //CONTENT_TYPE 和 CONTENT_ITEM_TYPE :  基本格式是type/subtype ， 因为Android定义的MIME是自定义的符合运营商规定的MIME ， 所以对于type
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ckt.ipicture";
        public static final String CONTENT_ITME_TYPE = "vnd.android.cursor.item/vnd.ckt.ipicture";
        //DEFAULT_SORT ： 默认的排序 ，这里是按照title来排序
        public static final String DEFAULT_SORT = TITLE + " asc";

    }
}
