create table AssetVocabularyDepotEntryRel (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	assetVocabularyDepotEntryRelId LONG not null,
	companyId LONG,
	assetVocabularyId LONG,
	depotEntryId LONG,
	primary key (assetVocabularyDepotEntryRelId, ctCollectionId)
);