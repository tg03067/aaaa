package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import({ FeedCommentServiceImpl.class })
class FeedCommentServiceTest {
    @MockBean
    private FeedCommentMapper mapper;
    @Autowired
    private FeedCommentServiceImpl service;

    @Test
    @DisplayName("FeedComment Post")
    void postFeedComment() {
        FeedCommentPostReq p1 = new FeedCommentPostReq();
        p1.setFeedCommentId(10);
        long result1 = service.postFeedComment(p1);
        assertEquals(p1.getFeedCommentId(), result1);

        FeedCommentPostReq p2 = new FeedCommentPostReq();
        p1.setFeedCommentId(1);
        long result2 = service.postFeedComment(p2);
        assertEquals(p2.getFeedCommentId(), result2);

        verify(mapper).postFeedComment(p1);
        verify(mapper).postFeedComment(p2);
    }

    @Test
    @DisplayName("FeedComment Delete")
    void delFeedComment() {
        FeedCommentDeleteReq p1 =
                new FeedCommentDeleteReq(1,2);
        given(mapper.delFeedComment(p1)).willReturn(1);
        long result1 = service.delFeedComment(p1);
        assertEquals(1,  result1, "1. 먼가이상");
        verify(mapper, times(1)).delFeedComment(p1);

        FeedCommentDeleteReq p2 =
                new FeedCommentDeleteReq(19,29);
        given(mapper.delFeedComment(p2)).willReturn(0);
        long result2 = service.delFeedComment(p2);
        assertEquals(0, result2, "2. 먼가이상");
        verify(mapper, times(1)).delFeedComment(p2);

        FeedCommentDeleteReq p3 =
                new FeedCommentDeleteReq(100,200);
        given(mapper.delFeedComment(p3)).willReturn(100);
        long result3 = service.delFeedComment(p3);
        assertEquals(100, result3, "3. 먼가이상");
        verify(mapper, times(1)).delFeedComment(p3);
    }

    @Test
    @DisplayName("FeedCommentList Get")
    void getFeedComment() {
        long feedId = 1;
        long feedIdZero = 5;

        FeedCommentGetRes res1 = new FeedCommentGetRes(1,"1",
                "24-12-12", 1, "1길동", "test1.png");
        FeedCommentGetRes res2 = new FeedCommentGetRes(2,"2",
                "24-12-12", 2, "2길동", "test2.png");
        FeedCommentGetRes res3 = new FeedCommentGetRes(3,"3",
                "24-12-12", 3, "3길동", "test3.png");
        List<FeedCommentGetRes> listZero = Arrays.asList();
        List<FeedCommentGetRes> list = new ArrayList<>();
        list.add(res1);
        list.add(res2);
        list.add(res3);

        given(mapper.selFeedComment(feedId)).willReturn(list);
        given(mapper.selFeedComment(feedIdZero)).willReturn(listZero);
        List<FeedCommentGetRes> result1 = service.getFeedComment(feedId);
        List<FeedCommentGetRes> result2 = service.getFeedComment(feedIdZero);
        assertEquals(list, result1);
        assertEquals(listZero.size(), result2.size());
        verify(mapper, times(1)).selFeedComment(feedId);
        verify(mapper, times(1)).selFeedComment(feedIdZero);
    }
}