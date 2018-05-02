package com.mersiyanov.dmitry.yadg.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFileList implements Parcelable {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    protected ResponseFileList(Parcel in) {
        if (in.readByte() == 0) {
            limit = null;
        } else {
            limit = in.readInt();
        }
        if (in.readByte() == 0) {
            offset = null;
        } else {
            offset = in.readInt();
        }
    }

    public static final Creator<ResponseFileList> CREATOR = new Creator<ResponseFileList>() {
        @Override
        public ResponseFileList createFromParcel(Parcel in) {
            return new ResponseFileList(in);
        }

        @Override
        public ResponseFileList[] newArray(int size) {
            return new ResponseFileList[size];
        }
    };

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (limit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(limit);
        }
        if (offset == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(offset);
        }
    }

     public static class Item implements Parcelable {

        @SerializedName("antivirus_status")
        @Expose
        private String antivirusStatus;
        @SerializedName("file")
        @Expose
        private String file;
        @SerializedName("sha256")
        @Expose
        private String sha256;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("revision")
        @Expose
        private Long revision;
        @SerializedName("resource_id")
        @Expose
        private String resourceId;
        @SerializedName("modified")
        @Expose
        private String modified;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("media_type")
        @Expose
        private String mediaType;
        @SerializedName("path")
        @Expose
        private String path;
        @SerializedName("md5")
        @Expose
        private String md5;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("mime_type")
        @Expose
        private String mimeType;
        @SerializedName("size")
        @Expose
        private Integer size;
        @SerializedName("public_key")
        @Expose
        private String publicKey;
        @SerializedName("public_url")
        @Expose
        private String publicUrl;

        protected Item(Parcel in) {
            antivirusStatus = in.readString();
            file = in.readString();
            sha256 = in.readString();
            name = in.readString();
            created = in.readString();
            if (in.readByte() == 0) {
                revision = null;
            } else {
                revision = in.readLong();
            }
            resourceId = in.readString();
            modified = in.readString();
            preview = in.readString();
            mediaType = in.readString();
            path = in.readString();
            md5 = in.readString();
            type = in.readString();
            mimeType = in.readString();
            if (in.readByte() == 0) {
                size = null;
            } else {
                size = in.readInt();
            }
            publicKey = in.readString();
            publicUrl = in.readString();
        }

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel in) {
                return new Item(in);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };

        public String getAntivirusStatus() {
            return antivirusStatus;
        }

        public void setAntivirusStatus(String antivirusStatus) {
            this.antivirusStatus = antivirusStatus;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getSha256() {
            return sha256;
        }

        public void setSha256(String sha256) {
            this.sha256 = sha256;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public Long getRevision() {
            return revision;
        }

        public void setRevision(Long revision) {
            this.revision = revision;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPublicUrl() {
            return publicUrl;
        }

        public void setPublicUrl(String publicUrl) {
            this.publicUrl = publicUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(antivirusStatus);
            dest.writeString(file);
            dest.writeString(sha256);
            dest.writeString(name);
            dest.writeString(created);
            if (revision == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeLong(revision);
            }
            dest.writeString(resourceId);
            dest.writeString(modified);
            dest.writeString(preview);
            dest.writeString(mediaType);
            dest.writeString(path);
            dest.writeString(md5);
            dest.writeString(type);
            dest.writeString(mimeType);
            if (size == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(size);
            }
            dest.writeString(publicKey);
            dest.writeString(publicUrl);
        }
    }
}