package ibf2022.batch2.csf.backend.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Archive;

@Repository
public class ArchiveRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Archive recordBundle(String name, String title, String comments, List<String> urls) {
        Archive archive = new Archive();
        archive.setBundleId(UUID.randomUUID().toString().substring(0, 8));
        archive.setDate(new Date());
        archive.setName(name);
        archive.setTitle(title);
        archive.setComments(comments);
        archive.setUrls(urls);

        return mongoTemplate.insert(archive, "archives");
    }

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Archive getBundleByBundleId(String bundleId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("bundleId").is(bundleId));
		return mongoTemplate.findOne(query, Archive.class, "archives");
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundles(String bundleId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("bundleId").is(bundleId));
		return mongoTemplate.findOne(query, Archive.class, "archives");
	}


}
