package unittest.mock.systems.tags;

import tech.fastj.systems.tags.TagHandler;

import java.util.ArrayList;
import java.util.List;

public class MockTagHandler implements TagHandler<MockTaggableEntity> {

    private final List<MockTaggableEntity> taggableEntities = new ArrayList<>();

    @Override
    public List<MockTaggableEntity> getTaggableEntities() {
        return taggableEntities;
    }
}
