package com.mountblue.blog.impl;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import com.mountblue.blog.entity.Tag;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void savePost(Post post, String postTags) {
        String[] tagArray = postTags.split(",");
        List<Tag> tags = new ArrayList<>();
        for (String tag : tagArray) {
            System.out.println("*********in savePost()********");
            System.out.println("tags are ="+tag);
            Tag tagObject = new Tag();
            tagObject.setName(tag);
            tags.add(tagObject);
        }
//        post.setTags(tags);
        System.out.println("tags are ="+tags.toString());
        System.out.println("post is "+post.toString());
        System.out.println("to be saved is"+post);
        Post newPost= new Post(post.getTitle(),post.getExcerpt(),post.getContent(),post.getAuthor(),tags);
        System.out.println("old post is ="+newPost);
        newPost.setTags(tags);
        post.setTags(tags);
        System.out.println("post is "+post);
        System.out.println("new post is ="+newPost);

//        postRepository.save(newPost); or below any
        postRepository.save(post);

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




    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize,String sortField,String sortDirection) {
       Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                   :Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        return this.postRepository.findAll(pageable);
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
