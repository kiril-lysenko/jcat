package com.self.education.catinfo.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest implements Pageable {

    private final Integer offset;
    private final Integer limit;
    private final Sort sort;

    public OffsetBasedPageRequest(final Integer offset, final Integer limit, final Sort sort) {
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public OffsetBasedPageRequest(final Integer offset, final Integer limit, final Sort.Direction order,
            final Attribute attribute) {
        this(offset, limit, Sort.by(order, attribute.getName()));
    }

    public OffsetBasedPageRequest(final Integer offset, final Integer limit) {
        this(offset, limit, Sort.by(Sort.Direction.ASC, Attribute.ID.getName()));
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest((int) (getOffset() + this.getPageSize()), this.getPageSize(), this.getSort());
    }

    public Pageable previous() {
        return hasPrevious() ?
                new OffsetBasedPageRequest((int) (getOffset() - this.getPageSize()), this.getPageSize(),
                        this.getSort()) :
                this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), this.getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new OffsetBasedPageRequest(pageNumber * this.getPageSize(), this.getPageSize(), this.getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
