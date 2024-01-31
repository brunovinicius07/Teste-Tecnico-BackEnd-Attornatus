package com.attornatus.people.controllers;

import com.attornatus.people.models.dto.request.PeopleRequestDto;
import com.attornatus.people.models.dto.response.PeopleResponseDto;
import com.attornatus.people.services.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Operation(summary = "Registrar uma nova pessoa", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Registro da pessoa realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado a acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @PostMapping
    public ResponseEntity<Object> registerPeople(@RequestBody @Valid PeopleRequestDto peopleRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(peopleService.registerPeople(peopleRequestDto));
    }

    @Operation(summary = "Buscar todos os registros das pessoas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK:Busca realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized:Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @GetMapping
    public ResponseEntity<Object> getAllPeople() {
        return ResponseEntity.ok(peopleService.getAllPeople());
    }

    @Operation(summary = "Buscar o registro de uma pessoa por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Busca realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @GetMapping("/{idPeople}")
    public ResponseEntity<Object> getPeopleById(@PathVariable Long idPeople) {
        return ResponseEntity.ok(peopleService.getPeopleById(idPeople));
    }

    @Operation(summary = "Editar os dados de uma pessoa por id", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Atualização realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @PutMapping("/{idPeople}")
    public ResponseEntity<Object> updatePeople(@PathVariable Long idPeople, @RequestBody
    @Valid PeopleRequestDto peopleRequestDto) {
        return ResponseEntity.ok(peopleService.updatePeople(idPeople, peopleRequestDto));
    }

    @Operation(summary = "Deletar o registro da pessoa por id", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @DeleteMapping("/{idPerson}")
    public ResponseEntity<Object> deletePeople(@PathVariable Long idPerson) {
        return ResponseEntity.ok(peopleService.deletePeople(idPerson));
    }
}
