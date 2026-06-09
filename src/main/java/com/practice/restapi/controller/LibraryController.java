package com.practice.restapi.controller;

import com.practice.restapi.common.ResponseMessage;
import com.practice.restapi.dto.BookDTO;
import com.practice.restapi.dto.MemberDTO;
import com.practice.restapi.dto.RentalDTO;
import com.practice.restapi.dto.RentalRequest;
import com.practice.restapi.enums.BookStatus;
import com.practice.restapi.enums.RentalStatus;
import com.practice.restapi.exception.BookAlreadyRentedException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {

    private final List<MemberDTO> members = new ArrayList<>();
    private final List<BookDTO> books = new ArrayList<>();
    private final List<RentalDTO> rentals = new ArrayList<>();

    private int memberSeq = 1;
    private int bookSeq = 1;
    private int rentalSeq = 1;

    public LibraryController() {

        members.add(new MemberDTO(
                memberSeq++,
                "reader01",
                "홍길동",
                "reader01@test.com",
                LocalDate.now()
        ));

        books.add(new BookDTO(
                bookSeq++,
                "Spring REST API 입문",
                "오지라퍼",
                "979-11-1111-111-1",
                BookStatus.AVAILABLE,
                LocalDate.of(2025, 3, 10)
        ));
    }


    @GetMapping("/members")
    public ResponseEntity<ResponseMessage> getMembers(
            @RequestParam(required = false) String name) {

        List<MemberDTO> result = members.stream()
                .filter(member ->
                        name == null || member.getName().contains(name))
                .toList();

        return ResponseEntity.ok(new ResponseMessage(200, "회원 목록 조회 성공", Map.of("members", result)));
    }

    @GetMapping("/members/{memberNo}")
    public ResponseEntity<ResponseMessage> getMember(@PathVariable int memberNo) {

        MemberDTO member = findMember(memberNo);

        return ResponseEntity.ok(new ResponseMessage(200, "회원 조회 성공", Map.of("member", member)));
    }

    @PostMapping("/members")
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberDTO member) {

        member.setMemberNo(memberSeq++);
        member.setJoinedAt(LocalDate.now());

        members.add(member);

        URI location = URI.create("/api/v1/library/members/" + member.getMemberNo());

        return ResponseEntity.created(location).build();
    }



    @GetMapping("/books")
    public ResponseEntity<ResponseMessage> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) BookStatus status) {

        List<BookDTO> result = books.stream()
                .filter(book -> title == null || book.getTitle().contains(title))
                .filter(book -> status == null || book.getStatus() == status)
                .toList();

        return ResponseEntity.ok(new ResponseMessage(200, "도서 목록 조회 성공", Map.of("books", result)));
    }


    @GetMapping("/books/{bookNo}")
    public ResponseEntity<ResponseMessage> getBook(@PathVariable int bookNo) {

        BookDTO book = findBook(bookNo);

        return ResponseEntity.ok(new ResponseMessage(200, "도서 조회 성공", Map.of("book", book)));
    }


    @PostMapping("/books")
    public ResponseEntity<Void> createBook(@Valid @RequestBody BookDTO book) {

        book.setBookNo(bookSeq++);
        book.setStatus(BookStatus.AVAILABLE);

        books.add(book);

        URI location = URI.create("/api/v1/library/books/" + book.getBookNo());

        return ResponseEntity.created(location).build();
    }



    @PostMapping("/rentals")
    public ResponseEntity<Void> rentBook(@Valid @RequestBody RentalRequest request) {

        MemberDTO member = findMember(request.getMemberNo());
        BookDTO book = findBook(request.getBookNo());

        if (book.getStatus() == BookStatus.RENTED) {
            throw new BookAlreadyRentedException("이미 대여 중인 도서입니다.");
        }

        RentalDTO rental = new RentalDTO();

        rental.setRentalNo(rentalSeq++);
        rental.setMemberNo(member.getMemberNo());
        rental.setBookNo(book.getBookNo());
        rental.setRentedAt(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(14));
        rental.setReturnedAt(null);
        rental.setStatus(RentalStatus.RENTED);

        rentals.add(rental);

        book.setStatus(BookStatus.RENTED);

        URI location = URI.create("/api/v1/library/rentals/" + rental.getRentalNo());

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/rentals/{rentalNo}")
    public ResponseEntity<ResponseMessage> getRental(@PathVariable int rentalNo){

        RentalDTO rental = findRental(rentalNo);

        return ResponseEntity.ok(new ResponseMessage(200, "대여 조회 성공", Map.of("rental", rental)));
    }
}
