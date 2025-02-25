create table AssetTagDepotEntryRel (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	assetTagDepotEntryRelId LONG not null,
	companyId LONG,
	assetTagId LONG,
	depotEntryId LONG,
	primary key (assetTagDepotEntryRelId, ctCollectionId)
);