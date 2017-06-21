package com.apress.v1.controller;

import com.apress.domain.Poll;
import com.apress.dto.error.ErrorDetail;
import com.apress.exception.ResourceNotFoundException;
import com.apress.repository.PollRepository;

import javax.inject.Inject;
import javax.validation.Valid;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by anthonyjones on 6/19/17.
 */
@RestController("pollControllerv1")
@RequestMapping(value = "/v1/")
@Api(value = "polls", description = "Poll API")//Swagger, saying it's being hosted at /polls
public class PollController {

    @Inject//other than autowire
    private PollRepository pollRepository;

    @RequestMapping(value = "/polls", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all the polls", response = Poll.class,
            responseContainer = "List")
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves a Poll associated with the pollId", response = Poll.class)
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        Poll p = pollRepository.findOne(pollId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @RequestMapping(value = "/polls", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new Poll", notes = "The newly created poll Id will be sent" +
            "in the location response header", response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Poll Created Successfully",
            response = Void.class),
            @ApiResponse(code = 500, message = "Error creating Poll", response = ErrorDetail.class)})
    public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);

        // Set the location header for the newly created resource
        //This is also letting the client know the path of the newly created poll
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                //from currentrequest copies info from the httpservletrequest
                //like the host, schema and port
                .fromCurrentRequest()
                //path appends the passed-in path parameter to the existing path in the builder
                .path("/{id}")
                //buildandExpand basically  build an uricompontes instace and
                // replaces any path variable with a passed-in value
                .buildAndExpand(poll.getId())
                //This invokes the toUri method on the Components ato generat the final Uri
                .toUri();
        responseHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);

    }


    protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
        Poll poll = pollRepository.findOne(pollId);
        if (poll == null) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
    }


    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.delete(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}