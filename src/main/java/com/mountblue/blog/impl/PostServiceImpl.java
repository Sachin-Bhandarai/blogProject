package com.mountblue.blog.impl;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.service.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        super();
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        if (postRepository.findById(id).isPresent()) {
            return postRepository.findById(id).get();
        }
         throw new RuntimeException("not found");
    }

    @Override
    public void addCommentByPostId(Long postId, String name, String email, String commentData) {
        Post post = getPostById(postId);
        Comment comment = createComment(post,name, email, commentData);
        post.getComments().add(comment);
        postRepository.save(post);

    }

    @Override
    public void deleteCommentByCommentId(Long postId, Long commentId) {
        Post post = getPostById(postId);
        List<Comment> comments = post.getComments();
        comments.removeIf(comment->comment.getId().equals(commentId));
        postRepository.save(post);
    }

    @Override
    public void updateCommentByCommentId(Long postId, Long commentId,String commentData) {
        Post post = getPostById(postId);
        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
            if(comment.getId().equals(commentId)){
                comment.setComment(commentData);
                break;
            }
        }
        postRepository.save(post);

    }

    @Override
    public Comment getCommentByPostId(Long postId,Long commentId) {
        Post post = getPostById(postId);
        List<Comment> comments = post.getComments();
//        Comment comment = comments.stream().filter(a -> a.getId() == commentId).collect(Collectors.toList()).get(0);
        for(Comment comment : comments) {
            if(comment.getId().equals(commentId)) {
                return comment;
            }
        }
        throw new RuntimeException("not found");


    }

    @Override
    public void deletePostById(Long id) {
        postRepository.delete(getPostById(id));
    }

    @Override
    public void updatePost(Long postId,String newPost) {
        Post post = getPostById(postId);
        post.setContent(newPost);
        postRepository.save(post);
    }

    @Override
    public List<Post> findAll(String field) {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }


    private static Comment createComment(Post post, String name, String email, String commentData) {
        Comment comment = new Comment();
        comment.setName(name);
        comment.setEmail(email);
        comment.setComment(commentData);
        comment.setPost(post);
        return comment;
    }

    public void save(Post post) {
        postRepository.save(post);
    }


}
