
openmrs-module-mergepatientdata
===============================

OpenMRS module that is a solution to un consistent internet connections to some OpenMRS implementations using the current OpenMRS SYNC module. It merges patient data which isn't currently implemented in the sync module.

There are individual installations where each installation is based at a facility (no guarantee of a consistent Internet connection), and is at the same version (OpenMRS, HTML forms, concepts and other metadata) - but there is a need to bring the patient records (or extracts) together to a central database. Site level users and metadata are not synced, neither are concepts and forms, as they are expected to be similar. The merged data (father instance) would be read-only, used reporting and analysis purposes.