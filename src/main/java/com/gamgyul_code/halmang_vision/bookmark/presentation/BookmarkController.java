package com.gamgyul_code.halmang_vision.bookmark.presentation;

import com.gamgyul_code.halmang_vision.bookmark.application.BookmarkService;
import com.gamgyul_code.halmang_vision.global.utils.AuthPrincipal;
import com.gamgyul_code.halmang_vision.member.dto.ApiMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
@Tag(name = "bookmark", description = "북마크 API")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "관광지 북마크 생성", description = "해당 회원에게 관광지 북마크를 생성한다.")
    @PostMapping("/spots/{spotId}")
    public void createSpotBookmark(@PathVariable Long spotId, @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        bookmarkService.createSpotBookmark(spotId, apiMember);
    }
}
