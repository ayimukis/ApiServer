package com.ynetech.apiserver.controller;

import com.ynetech.apiserver.dto.ResponseDTO;
import com.ynetech.apiserver.dto.TodoDTO;
import com.ynetech.apiserver.model.TodoEntity;
import com.ynetech.apiserver.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @package : com.ynetech.apiserver.controller
* @name : TodoController.java
* @date : 2022/06/21 1:44 PM
* @author : hyuk
* @version : 1.0.0
* @modifyed :
**/
@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    private TodoService service;

    /**
    * @name : testTodo
    * @comment :
    * @date : 2022/06/21 1:44 PM
    * @author : hyuk
    * @version : 1.0.0
    * @modifyed :
    **/
    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService(); // 테스트 서비스 사용
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    /**
    * @package : com.ynetech.apiserver.controller
    * @name : TodoController.java
    * @date : 2022/06/21 1:44 PM
    * @author : hyuk
    * @version : 1.0.0
    * @modifyed : 투두 저장
    **/
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";  // temporary user id.
            
            //  (1) TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);
            
            //  (2) id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);
            
            //  (3) 임시 사용자 아이디를 설정해 준다.
            entity.setUserId(temporaryUserId);
            
            //  (4) 서비스를 이용해 투두 엔티티를 생성한다.
            List<TodoEntity> entities = service.create(entity);

            //  (5) 자바 스트림을 이용해 리턴 된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //  (6) 변환 된 ToidoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //  (7) ResponseDTO를 리턴한다.
            return  ResponseEntity.ok().body(response);
        } catch (Exception e) {

            //  (8) 혹시 예외가 있는 경우 dto 대시 error에 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
    * @name : retrieveTodoList
    * @comment : 투두 리스트
    * @date : 2022/06/21 2:05 PM
    * @author : hyuk
    * @version : 1.0.0
    * @modifyed :
    **/
    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";  // temporary user id.

        //  (1) 서비스 메서드의 retrieve() 메서드를 사용해 투두 리스트를 가져온다.
        List<TodoEntity> entities = service.retrieve(temporaryUserId);

        //  (2) 자바 스트림을 이용해 리턴 된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());;

        //  (3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화 한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //  (4) ResponseDTO를 리턴한다.
        return ResponseEntity.ok().body(response);
    }

    /**
    * @name : updateTodo
    * @comment : 투두 수정
    * @date : 2022/06/21 2:05 PM
    * @author : hyuk
    * @version : 1.0.0
    * @modifyed :
    **/
    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user";  // temporary user id.

        //  (1) dto를 entity로 변환한다.
        TodoEntity entity = TodoDTO.toEntity(dto);

        //  (2) id를 TemporaryUserId로 초기화한다. 인증 인가에서 수정 예정
        entity.setUserId(temporaryUserId);

        //  (3) 서비스를 이용해 entity를 업데이트한다.
        List<TodoEntity> entities = service.update(entity);

        //  (4) 자바 스트림을 이용해 리턴 된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //  (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //  (6) ResponseDTO를 리턴한다.
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";  // temporary user id.

            //  (1) TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);

            //  (2) 임시 사용자 아이디를 설정해 준다.
            entity.setUserId(temporaryUserId);

            //  (3) 서비스를 이용해 entity를 삭제한다.
            List<TodoEntity> entities = service.delete(entity);

            //  (4) 자바 스트림을 이용해 리턴 된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //  (5) 변환 된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //  (6) ResponseDTO를 리턴한다.
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            //  (7) 혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
